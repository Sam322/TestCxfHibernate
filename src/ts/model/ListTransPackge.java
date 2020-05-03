package ts.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

//lyy ÐÂÔö
@XmlRootElement(name="ListTransPackge")
public class ListTransPackge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4253960204603624324L;
	
	List<TransPackage> transPackageList;

    public List<TransPackage> getTransPackageList() {
        return transPackageList;
    }

    public void setTransPackageList(List<TransPackage> transPackageList) {
        this.transPackageList = transPackageList;
    }

}
