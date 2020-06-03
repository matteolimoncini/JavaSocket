public class distrPrezzo {
    private int distributore;
    private int prezzo;
    public distrPrezzo(int distributore, int prezzo) {
        this.distributore=distributore;
        this.prezzo=prezzo;
    }

    public int getDistributore() {
        return distributore;
    }

    public void setDistributore(int distributore) {
        this.distributore = distributore;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
}
