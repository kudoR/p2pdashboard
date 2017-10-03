package eu.ffs.repository.entity;

import eu.ffs.repository.ConfigId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by j on 05.05.17.
 */
@Entity
public class DashboardConfiguration implements Serializable {
    @Id
    ConfigId configurationId;

    public DashboardConfiguration() {

    }

    public DashboardConfiguration(ConfigId configurationId) {
        this.configurationId = configurationId;
    }

    public ConfigId getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(ConfigId configurationId) {
        this.configurationId = configurationId;
    }

    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    private boolean booleanValue;

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
