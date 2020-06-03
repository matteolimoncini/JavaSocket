public class Treno {
    private final int codiceTreno;
    private int ritardo;
    private char idStazioneUltimoAggiornamento;

    public Treno(int codiceTreno) {
        this.codiceTreno = codiceTreno;
        ritardo = 0;
        idStazioneUltimoAggiornamento = '-';
    }

    public int getCodiceTreno() {
        return codiceTreno;
    }

    @Override
    public String toString() {
        return "Treno{" + "stazione=" + idStazioneUltimoAggiornamento +
                ", ritardo=" + ritardo +
                '}';
    }

    public void setRitardo(int ritardo) {
        this.ritardo = ritardo;
    }

    public void setIdStazioneUltimoAggiornamento(char idStazioneUltimoAggiornamento) {
        this.idStazioneUltimoAggiornamento = idStazioneUltimoAggiornamento;
    }
}
