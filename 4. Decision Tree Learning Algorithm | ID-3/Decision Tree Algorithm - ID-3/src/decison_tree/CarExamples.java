package decison_tree;

public class CarExamples {
    private String buying;
    private String maint;
    private String doors;
    private String persons;
    private String lugBoot;
    private String safety;
    private String label;

    public CarExamples(String buying, String maint, String doors, String persons, String lugBoot, String safety, String label) {
        this.buying = buying;
        this.maint = maint;
        this.doors = doors;
        this.persons = persons;
        this.lugBoot = lugBoot;
        this.safety = safety;
        this.label = label;
    }

    public String getBuying() {
        return buying;
    }

    public String getMaint() {
        return maint;
    }

    public String getDoors() {
        return doors;
    }

    public String getPersons() {
        return persons;
    }

    public String getLugBoot() {
        return lugBoot;
    }

    public String getSafety() {
        return safety;
    }

    public String getLabel() {
        return label;
    }


    public void setBuying(String buying) {
        this.buying = buying;
    }

    public void setMaint(String maint) {
        this.maint = maint;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public void setLugBoot(String lugBoot) {
        this.lugBoot = lugBoot;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // Define a method to get the attribute value by attribute name
    public String getAttribute(String attributeName) {
        if(attributeName== "buying"){
            return buying;
        }
        else if(attributeName== "maint"){
            return maint;
        }
        else if(attributeName== "doors"){
            return doors;
        }
        else if(attributeName== "persons"){
            return persons;
        }
        else if(attributeName== "lugBoot"){
            return lugBoot;
        }
        else if(attributeName== "safety"){
            return safety;
        }
        else{
            return null;
        }
    }
}
