package innovation.labi2;


import java.io.Serializable;

public class Poster implements Serializable {


    private String wbid;
    private String identifikationsnummer;
    private String titel;
    private String material;
    private String erscheinungsjahr;
    private String format;
    private String inhalt;
    private String url;


    public Poster(String wbid, String identifikationsnummer, String titel, String material, String erscheinungsjahr, String format, String inhalt, String url) {
        this.wbid = wbid;
        this.identifikationsnummer = identifikationsnummer;
        this.titel = titel;
        this.material = material;
        this.erscheinungsjahr = erscheinungsjahr;
        this.format = format;
        this.inhalt = inhalt;
        this.url = url;
    }

    public String getWbid() {
        return wbid;
    }

    public void setWbid(String wbid) {
        this.wbid = wbid;
    }

    public String getIdentifikationsnummer() {
        return identifikationsnummer;
    }

    public void setIdentifikationsnummer(String identifikationsnummer) {
        this.identifikationsnummer = identifikationsnummer;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public void setErscheinungsjahr(String erscheinungsjahr) {
        this.erscheinungsjahr = erscheinungsjahr;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
