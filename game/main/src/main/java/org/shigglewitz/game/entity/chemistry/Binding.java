package org.shigglewitz.game.entity.chemistry;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
public class Binding implements Serializable {
    private static final long serialVersionUID = 8081097302220826229L;
    private List<ElementBinding> elements;

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    public List<ElementBinding> getElements() {
        return elements;
    }

    public void setElements(List<ElementBinding> elements) {
        this.elements = elements;
    }

    public static class ElementBinding implements Serializable {
        private static final long serialVersionUID = 856239111777652067L;
        private int id;
        private Element element;

        @XmlElement(name = "id")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlElement(name = "fields")
        public Element getElement() {
            return element;
        }

        public void setElement(Element element) {
            this.element = element;
        }
    }
}
