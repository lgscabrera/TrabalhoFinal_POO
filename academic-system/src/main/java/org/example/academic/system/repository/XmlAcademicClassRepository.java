package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Repositório que persiste turmas e avaliações em arquivo XML (US-2373).
 * Usa a API DOM padrão do Java — sem dependência externa.
 */
public class XmlAcademicClassRepository implements AcademicClassRepository {

    private static final Logger logger = LoggerFactory.getLogger(XmlAcademicClassRepository.class);
    private static final String FILE_PATH = "academic_data.xml";

    @Override
    public void save(List<AcademicClass> classes) {
        logger.info("Iniciando persistência em XML. Arquivo: '{}'", FILE_PATH);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("academicSystem");
            doc.appendChild(root);

            for (AcademicClass academicClass : classes) {
                Element classElement = doc.createElement("class");

                Element code = doc.createElement("code");
                code.setTextContent(academicClass.getCode());
                classElement.appendChild(code);

                Element title = doc.createElement("title");
                title.setTextContent(academicClass.getTitle());
                classElement.appendChild(title);

                Element assessmentsElement = doc.createElement("assessments");
                for (Assessment assessment : academicClass.getAssessments()) {
                    Element assessmentElement = doc.createElement("assessment");

                    Element type = doc.createElement("type");
                    type.setTextContent(assessment.getType());
                    assessmentElement.appendChild(type);

                    Element value = doc.createElement("value");
                    value.setTextContent(String.valueOf(assessment.getValue()));
                    assessmentElement.appendChild(value);

                    Element weight = doc.createElement("weight");
                    weight.setTextContent(String.valueOf(assessment.getWeight()));
                    assessmentElement.appendChild(weight);

                    assessmentsElement.appendChild(assessmentElement);
                }
                classElement.appendChild(assessmentsElement);
                root.appendChild(classElement);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

            logger.info("Persistência em XML concluída com sucesso. {} turma(s) salva(s).", classes.size());

        } catch (Exception e) {
            logger.error("Erro ao salvar dados em XML: {}", e.getMessage());
            throw new RuntimeException("Falha ao salvar dados no arquivo XML: " + e.getMessage(), e);
        }
    }
}
