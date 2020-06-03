public class forecastItem implements Comparable<forecastItem> {
    private int feedback = 0;
    private char tempo = '-';
    private char id;

    public forecastItem(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public void setTempo(char tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString() {
        return "{" + "id= " + id +
                ", tempo= " + tempo +
                ", feedback= " + feedback +
                "}";
    }

    @Override
    public int compareTo(forecastItem o) {
        return Integer.compare(o.getFeedback(), this.getFeedback());
    }
}
