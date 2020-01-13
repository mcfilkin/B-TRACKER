package info;

import javafx.beans.property.StringProperty;

public class Bug {
    private StringProperty id;
    private StringProperty shortDescription;
    private StringProperty fullDescription;
    private StringProperty stepsToReproduce;
    private StringProperty reproducibility;
    private StringProperty severity;
    private StringProperty priority;
    private StringProperty comment;
    private StringProperty create;
    private StringProperty update;
    private StringProperty status;

    public StringProperty getId() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(StringProperty shortDescription) {
        this.shortDescription = shortDescription;
    }


    public StringProperty getUpdate() {
        return update;
    }

    public void setUpdate(StringProperty update) {
        this.update = update;
    }

    public StringProperty getCreate() {
        return create;
    }

    public void setCreate(StringProperty create) {
        this.create = create;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }

    public StringProperty getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(StringProperty fullDescription) {
        this.fullDescription = fullDescription;
    }

    public StringProperty getComment() {
        return comment;
    }

    public void setComment(StringProperty comment) {
        this.comment = comment;
    }

    public StringProperty getStepsToReproduce() {
        return stepsToReproduce;
    }

    public void setStepsToReproduce(StringProperty stepsToReproduce) {
        this.stepsToReproduce =stepsToReproduce;
    }

    public StringProperty getReproducibility() {
        return reproducibility;
    }

    public void setReproducibility(StringProperty reproducibility) {
        this.reproducibility =reproducibility;
    }

    public StringProperty getSeverity() {
        return severity;
    }

    public void setSeverity(StringProperty severity) {
        this.severity =severity;
    }

    public StringProperty getPriority() {
        return priority;
    }

    public void setPriority(StringProperty priority) {
        this.priority =priority;
    }
}
