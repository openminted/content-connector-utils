package eu.openminted.content.connector.utils.faceting;

import eu.openminted.registry.domain.DocumentTypeEnum;
import eu.openminted.registry.domain.PublicationTypeEnum;
import eu.openminted.registry.domain.RightsStatementEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OMTDFacetLabels {
    private Map<OMTDFacetEnum, String> facetLabelsFromEnum = new HashMap<>();

    private Map<RightsStatementEnum, String> rightsStmtLabelFromEnum = new HashMap<>();
    private Map<String, RightsStatementEnum> rightsStmtEnumFromLabel = new HashMap<>();

    private Map<PublicationTypeEnum, String> publicationTypeLabelFromEnum = new HashMap<>();
    private Map<String, PublicationTypeEnum> publicationTypeEnumFromLabel = new HashMap<>();

    private Map<DocumentTypeEnum, String> documentTypeLabelFromEnum = new HashMap<>();
    private Map<String, DocumentTypeEnum> documentTypeFromLabel = new HashMap<>();


    public OMTDFacetLabels() {
        facetLabelsFromEnum.put(OMTDFacetEnum.PUBLICATION_TYPE, "Publication Type");
        facetLabelsFromEnum.put(OMTDFacetEnum.PUBLICATION_YEAR, "Publication Year");
        facetLabelsFromEnum.put(OMTDFacetEnum.RIGHTS_STMT_NAME, "Rights Statement");
        facetLabelsFromEnum.put(OMTDFacetEnum.RIGHTS, "Rights");
        facetLabelsFromEnum.put(OMTDFacetEnum.DOCUMENT_LANG, "Language");
        facetLabelsFromEnum.put(OMTDFacetEnum.DOCUMENT_TYPE, "Document Type");
        facetLabelsFromEnum.put(OMTDFacetEnum.SOURCE, "Content Source");

        /* rightsStmt Labels */
        rightsStmtLabelFromEnum.put(RightsStatementEnum.OPEN_ACCESS, "Open Access");
        rightsStmtLabelFromEnum.put(RightsStatementEnum.RESTRICTED_ACCESS, "Restricted Access");

        /* rightsStmtEnum From Label */
        rightsStmtEnumFromLabel.put("Open Access", RightsStatementEnum.OPEN_ACCESS);
        rightsStmtEnumFromLabel.put("Restricted Access", RightsStatementEnum.RESTRICTED_ACCESS);

        /* publicationType Labels */
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.ANNOTATION, "Annotation");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.RESEARCH_ARTICLE, "Research Article");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.RESEARCH_PROPOSAL, "Research Proposal");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.DOCTORAL_THESIS, "Doctoral Thesis");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.THESIS, "Thesis");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.CONFERENCE_OBJECT, "Conference Object");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.PRE_PRINT, "Pre Print");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.RESEARCH_REPORT, "Research Report");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.BOOK, "Book");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.MASTER_THESIS, "Master Thesis");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.BOOK_PART, "Book Part");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.REPORT, "Report");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.REVIEW, "Review");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.BACHELOR_THESIS, "Bachelor Thesis");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.LECTURE, "Lecture");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.PATENT, "Patent");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.CONTRIBUTION_TO_JOURNAL, "Contribution to Journal");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.DATA_PAPER, "Data Paper");
        publicationTypeLabelFromEnum.put(PublicationTypeEnum.OTHER, "Other");

        /* publicationType from Labels */
        publicationTypeEnumFromLabel.put("Annotation", PublicationTypeEnum.ANNOTATION);
        publicationTypeEnumFromLabel.put("Research Article", PublicationTypeEnum.RESEARCH_ARTICLE);
        publicationTypeEnumFromLabel.put("Research Proposal", PublicationTypeEnum.RESEARCH_PROPOSAL);
        publicationTypeEnumFromLabel.put("Thesis", PublicationTypeEnum.THESIS);
        publicationTypeEnumFromLabel.put("Doctoral Thesis", PublicationTypeEnum.DOCTORAL_THESIS);
        publicationTypeEnumFromLabel.put("Conference Object", PublicationTypeEnum.CONFERENCE_OBJECT);
        publicationTypeEnumFromLabel.put("Pre Print", PublicationTypeEnum.PRE_PRINT);
        publicationTypeEnumFromLabel.put("Research Report", PublicationTypeEnum.RESEARCH_REPORT);
        publicationTypeEnumFromLabel.put("Book", PublicationTypeEnum.BOOK);
        publicationTypeEnumFromLabel.put("Master Thesis", PublicationTypeEnum.MASTER_THESIS);
        publicationTypeEnumFromLabel.put("Book Part", PublicationTypeEnum.BOOK_PART);
        publicationTypeEnumFromLabel.put("Report", PublicationTypeEnum.REPORT);
        publicationTypeEnumFromLabel.put("Review", PublicationTypeEnum.REVIEW);
        publicationTypeEnumFromLabel.put("Bachelor Thesis", PublicationTypeEnum.BACHELOR_THESIS);
        publicationTypeEnumFromLabel.put("Lecture", PublicationTypeEnum.LECTURE);
        publicationTypeEnumFromLabel.put("Patent", PublicationTypeEnum.PATENT);
        publicationTypeEnumFromLabel.put("Contribution to Journal", PublicationTypeEnum.CONTRIBUTION_TO_JOURNAL);
        publicationTypeEnumFromLabel.put("Data Paper", PublicationTypeEnum.DATA_PAPER);
        publicationTypeEnumFromLabel.put("Other", PublicationTypeEnum.OTHER);

        /* documentType Labels */
        documentTypeLabelFromEnum.put(DocumentTypeEnum.WITH_ABSTRACT_ONLY, "Abstract");
        documentTypeLabelFromEnum.put(DocumentTypeEnum.WITH_FULL_TEXT, "Fulltext");

        /* documentType From Labels */
        documentTypeFromLabel.put("Abstract", DocumentTypeEnum.WITH_ABSTRACT_ONLY);
        documentTypeFromLabel.put("Fulltext", DocumentTypeEnum.WITH_FULL_TEXT);
    }

    public String getFacetLabelsFromEnum(OMTDFacetEnum omtdFacetEnum) {
        if (facetLabelsFromEnum.containsKey(omtdFacetEnum))
            return facetLabelsFromEnum.get(omtdFacetEnum);
        return "";
    }

    public String getRightsStmtLabelFromEnum(RightsStatementEnum rightsStatementEnum) {
        if (rightsStmtLabelFromEnum.containsKey(rightsStatementEnum))
            return rightsStmtLabelFromEnum.get(rightsStatementEnum);
        return "";
    }

    public Map<String, RightsStatementEnum> getRightsStmtEnumFromLabel() {
        return rightsStmtEnumFromLabel;
    }

    public String getPublicationTypeLabelFromEnum(PublicationTypeEnum publicationTypeEnum) {
        if (publicationTypeLabelFromEnum.containsKey(publicationTypeEnum))
            return publicationTypeLabelFromEnum.get(publicationTypeEnum);
        return "";
    }

    public Map<String, PublicationTypeEnum> getPublicationTypeEnumFromLabel() {
        return publicationTypeEnumFromLabel;
    }

    public String getDocumentTypeLabelFromEnum(DocumentTypeEnum documentTypeEnum) {
        if (documentTypeLabelFromEnum.containsKey(documentTypeEnum))
            return documentTypeLabelFromEnum.get(documentTypeEnum);
        return "";
    }

    public Map<String, DocumentTypeEnum> getDocumentTypeFromLabel() {
        return documentTypeFromLabel;
    }
}
