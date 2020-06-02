public class forecastItem {
    private int feedback=0;
    private char tempo='-';
    private char id='-';

    public forecastItem(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public char getTempo() {
        return tempo;
    }

    public void setTempo(char tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("id= ").append(id);
        sb.append(", tempo= ").append(tempo);
        sb.append(", feedback= ").append(feedback);
        sb.append("}");
        return sb.toString();
    }

}
