package eu.openminted.content.connector.utils;

import eu.openminted.content.connector.utils.faceting.OMTDFacetEnum;
import eu.openminted.content.connector.SearchResult;
import eu.openminted.registry.core.domain.Facet;
import eu.openminted.registry.core.domain.Value;
import org.apache.commons.lang.WordUtils;

import java.util.*;
import java.util.stream.Collectors;

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

    private static List<Facet> mergeFacets(List<Facet> f1, List<Facet> f2) {
//        Map<String, Facet> mf1 = new HashMap<>();
        if (f1 != null && f2 != null)
            return new ArrayList<>(mergeFacets(
                    f1.stream().collect(Collectors.toMap(f -> WordUtils.capitalizeFully(f.getField()), f -> f)),
                    f2.stream().collect(Collectors.toMap(f -> WordUtils.capitalizeFully(f.getField()), f -> f)))
                    .values());

        if (f1 == null && f2 == null) return new ArrayList<>();

        if (f1 == null) return f2;
        else return f1;
    }

    private static Map<String, Facet> mergeFacets(Map<String, Facet> f1, Map<String, Facet> f2) {

        Map<String, Facet> temp = new HashMap<>();

        for (Map.Entry<String, Facet> e : f1.entrySet()) {
            Facet facet;
            String keyName = WordUtils.capitalizeFully(e.getKey());

            if (e.getKey().equalsIgnoreCase(OMTDFacetEnum.PUBLICATION_YEAR.value())) {
                facet = buildFacet(getPublicationYearFacet(e.getValue()));
            }
//            else if (e.getKey().equalsIgnoreCase(OMTDFacetEnum.DOCUMENT_LANG.value())) {
//                facet = buildFacet(getLanguageFacet(e.getValue()));
//            }
            else
                facet = buildFacet(e.getValue());

            for (Value value : facet.getValues()) {
                value.setValue(WordUtils.capitalizeFully(value.getValue()));
            }

            temp.put(keyName, facet);
        }

        for (Map.Entry<String, Facet> e : f2.entrySet()) {
            Facet facet;
            String keyName = WordUtils.capitalizeFully(e.getKey());

            if (e.getKey().equalsIgnoreCase(OMTDFacetEnum.PUBLICATION_YEAR.value())) {
                facet = buildFacet(getPublicationYearFacet(e.getValue()));
            }
//            else if (e.getKey().equalsIgnoreCase(OMTDFacetEnum.DOCUMENT_LANG.value())) {
//                facet = buildFacet(getLanguageFacet(e.getValue()));
//            }
            else {
                facet = buildFacet(e.getValue());
            }

            for (Value value : facet.getValues()) {
                value.setValue(WordUtils.capitalizeFully(value.getValue()));
            }

            if (temp.containsKey(e.getKey())) {
                temp.put(keyName, mergeFacet(temp.get(keyName), facet));
            } else {
                temp.put(keyName, facet);
            }

            Collections.sort(temp.get(keyName).getValues());
            Collections.reverse(temp.get(keyName).getValues());
        }

        return temp;
    }

    private static Facet mergeFacet(Facet f1, Facet f2) {
        Facet f = new Facet();
        Map<String, Integer> temp = new HashMap<>();
        Map<String, String> labels = new HashMap<>();

        f.setField(f1.getField());
        f.setLabel(f1.getLabel());
        f.setValues(new ArrayList<>());

        for (Value v : f1.getValues()) {
            String valueName = WordUtils.capitalizeFully(v.getValue());
            temp.put(valueName, v.getCount());
            labels.put(valueName, v.getLabel());
        }
        for (Value v : f2.getValues()) {
            String valueName = WordUtils.capitalizeFully(v.getValue());
            if (temp.containsKey(valueName))
                temp.put(valueName, v.getCount() + temp.get(WordUtils.capitalizeFully(v.getValue())));
            else {
                temp.put(valueName, v.getCount());
                labels.put(valueName, v.getLabel());
            }
        }

        for (Map.Entry<String, Integer> e : temp.entrySet()) {
            Value v = new Value();
            String keyName = WordUtils.capitalizeFully(e.getKey());

            v.setValue(keyName);
            v.setLabel(labels.get(keyName));
            v.setCount(e.getValue());

            f.getValues().add(v);
        }

        return f;
    }

    private static Facet getPublicationYearFacet(Facet publicationYearFacet) {
        for (Value value : publicationYearFacet.getValues()) {
            value.setValue(value.getValue().substring(0, 4));
        }
        return publicationYearFacet;
    }


//    private static Facet getLanguageFacet(Facet languageFacet) {
//
//        // Check this hashMap to convert ISO 639-2 to omtd ISO 639-1 (or ISO 639-3)
//        for (Value value : languageFacet.getValues()) {
//            if (value.getCount() > 0
//                    && LanguageConverter.getInstance().getISO639_3Naming().containsKey(value.getValue())) {
//                value.setValue(LanguageConverter.getInstance().getISO639_3Naming().get(value.getValue()));
//            }
//        }
//        return languageFacet;
//    }

    private static Facet buildFacet(Facet facet) {
        facet.setValues(mergeValues(facet));
        return facet;
    }

    private static List<Value> mergeValues(Facet facet) {
        Map<String, Value> tmpValues = new HashMap<>();

        for (Value value : facet.getValues()) {
            Value tmpValue = new Value();
            tmpValue.setCount(value.getCount());
            tmpValue.setValue(value.getValue());
            tmpValue.setLabel(value.getLabel());

            String keyName = WordUtils.capitalizeFully(tmpValue.getValue());

            if (tmpValues.containsKey(keyName)) {
                int currentCount = tmpValues.get(keyName).getCount();
                tmpValues.get(keyName).setCount(currentCount + tmpValue.getCount());
            } else {
                tmpValues.put(keyName, tmpValue);
            }
        }

        List<Value> values = new ArrayList<>(tmpValues.values());
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