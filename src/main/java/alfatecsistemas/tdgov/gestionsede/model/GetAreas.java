package alfatecsistemas.tdgov.gestionsede.model;

import java.util.List;

public class GetAreas {
    private List<Area> area;

    public List<Area> getEntries(){
        return this.area;
    }
    public void setEntries(List<Area> entries){
        this.area = entries;
    }
}