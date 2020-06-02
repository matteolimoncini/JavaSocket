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
