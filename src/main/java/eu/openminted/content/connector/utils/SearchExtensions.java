package eu.openminted.content.connector.utils;

import eu.openminted.content.connector.SearchResult;
import eu.openminted.registry.core.domain.Facet;
import eu.openminted.registry.core.domain.Value;
import org.apache.commons.lang.WordUtils;

import java.util.*;
import java.util.function.BinaryOperator;

public class SearchExtensions {

    public static void merge(SearchResult result1, SearchResult result2) {
        result1.setTotalHits(result1.getTotalHits() + result2.getTotalHits());
        result1.setFacets(mergeFacets(result1.getFacets(), result2.getFacets()));

        if (result1.getFrom() > result2.getFrom())
            result1.setFrom(result2.getFrom());
        if (result1.getTo() < result2.getTo())
            result1.setTo(result2.getTo());

        //TODO: how to merge results? do we need to?
    }

    private static Map<String, Facet> createMap(List<Facet> facetList) {
        Map<String, Facet> map = new HashMap<>();

        facetList.forEach(f -> {
            String key = WordUtils.capitalizeFully(f.getField());
            f.setValues(mergeValues(f));
            if (!map.containsKey(key)) {
                map.put(key, f);
            } else {
                f.getValues().forEach(v -> map.get(key).getValues().add(v));
            }
        });
        return map;
    }

    private static List<Facet> mergeFacets(List<Facet> f1, List<Facet> f2) {
        Map<String, Facet> mf1 = null;
        Map<String, Facet> mf2 = null;

        if (f1 != null)
            mf1 = createMap(f1);

        if (f2 != null)
            mf2 = createMap(f2);

        if (mf1 != null && mf2 != null) {
            return new ArrayList<>(mergeFacets(mf1, mf2, new BinaryOperator<Facet>() {
                @Override
                public Facet apply(Facet facet, Facet facet2) {


                    Facet mergedFacet = mergeFacet(facet, facet2);
                    Collections.sort(mergedFacet.getValues());
                    Collections.reverse(mergedFacet.getValues());
                    return mergedFacet;
                }
            }).values());
        }

        if (f1 == null && f2 == null) return new ArrayList<>();

        if (f1 == null) return f2;
        else return f1;
    }

    private static Map<String, Facet> mergeFacets(Map<String, Facet> f1, Map<String, Facet> f2, BinaryOperator<Facet> combiner) {
        f1.forEach((k, v) -> f2.merge(k, v, combiner));
        return f2;
    }

    private static Facet mergeFacet(Facet f1, Facet f2) {
        Facet f = new Facet();
        Map<String, Integer> mapCounts = new HashMap<>();
        Map<String, String> mapLabels = new HashMap<>();

        f.setField(f1.getField());
        f.setLabel(f1.getLabel());
        f.setValues(new ArrayList<>());



        for (Value v : f1.getValues()) {
            mapCounts.put(v.getValue(), v.getCount());
            mapLabels.put(v.getValue(), v.getLabel());
        }
        for (Value v : f2.getValues()) {
            if (mapCounts.containsKey(v.getValue()))
                mapCounts.put(v.getValue(), v.getCount() + mapCounts.get(v.getValue()));
            else {
                mapCounts.put(v.getValue(), v.getCount());
                mapLabels.put(v.getValue(), v.getLabel());
            }
        }

        for (Map.Entry<String, Integer> e : mapCounts.entrySet()) {
            Value v = new Value();
            v.setValue(e.getKey());
            v.setLabel(mapLabels.get(e.getKey()));
            v.setCount(e.getValue());

            f.getValues().add(v);
        }

        return f;
    }

    private static List<Value> mergeValues(Facet facet) {
        Map<String, Value> map = new HashMap<>();

        facet.getValues().forEach(v -> {
            v.setValue(WordUtils.capitalizeFully(v.getValue()));
            v.setLabel(WordUtils.capitalizeFully(v.getLabel()));
            String key = v.getValue();

            if (!map.containsKey(key)) {
                Value value = new Value(v.getValue(), v.getCount());
                value.setLabel(v.getLabel());
                map.put(key, value);
            } else {
                Value value = map.get(key);
                value.setCount(value.getCount() + v.getCount());
            }
        });

        List<Value> values = new ArrayList<>(map.values());
        Collections.sort(values);
        Collections.reverse(values);
        return values;
    }

    public static void printFacet(Facet facet) {
        System.out.println("field:" + facet.getField());
        System.out.println("values:[");
        for (Value value : facet.getValues()) {
            System.out.println(value.getValue());
            System.out.println(value.getCount());
        }
        System.out.println("]");
    }
}