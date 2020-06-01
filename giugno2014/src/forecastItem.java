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
}
