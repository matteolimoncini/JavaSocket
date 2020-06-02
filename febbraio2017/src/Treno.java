public class Treno {
    private final int codiceTreno;
    private int ritardo;
    private char idStazioneUltimoAggiornamento;

    public Treno(int codiceTreno) {
        this.codiceTreno = codiceTreno;
        ritardo=0;
        idStazioneUltimoAggiornamento ='-';
    }

    public int getCodiceTreno() {
        return codiceTreno;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Treno{");
        sb.append("stazione=").append(idStazioneUltimoAggiornamento);
        sb.append(", ritardo=").append(ritardo);
        sb.append('}');
        return sb.toString();
    }

    public int getRitardo() {
        return ritardo;
    }

    public void setRitardo(int ritardo) {
        this.ritardo = ritardo;
    }

    public char getIdStazioneUltimoAggiornamento() {
        return idStazioneUltimoAggiornamento;
    }

    public void setIdStazioneUltimoAggiornamento(char idStazioneUltimoAggiornamento) {
        this.idStazioneUltimoAggiornamento = idStazioneUltimoAggiornamento;
    }
}
