
package alfatecsistemas.tdgov.gestionsede.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//POJO
public class Category {

    @SerializedName("cat_uid")
    @Expose
    private String catUid;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @SerializedName("cat_total_processes")
    @Expose
    private Integer catTotalProcesses;

    public String getCatUid() {
        return catUid;
    }

    public void setCatUid(String catUid) {
        this.catUid = catUid;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getCatTotalProcesses() {
        return catTotalProcesses;
    }

    public void setCatTotalProcesses(Integer catTotalProcesses) {
        this.catTotalProcesses = catTotalProcesses;
    }

    @Override
    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catName == null) ? 0 : catName.hashCode());
		result = prime * result + ((catTotalProcesses == null) ? 0 : catTotalProcesses.hashCode());
		result = prime * result + ((catUid == null) ? 0 : catUid.hashCode());
		return result;      
	}

    @Override
    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (catName == null) {
			if (other.catName != null)
				return false;
		} else if (!catName.equals(other.catName))
			return false;
		if (catTotalProcesses == null) {
			if (other.catTotalProcesses != null)
				return false;
		} else if (!catTotalProcesses.equals(other.catTotalProcesses))
			return false;
		if (catUid == null) {
			if (other.catUid != null)
				return false;
		} else if (!catUid.equals(other.catUid))
			return false;
		return true;
    }
}
