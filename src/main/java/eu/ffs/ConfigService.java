package eu.ffs;

import eu.ffs.repository.ConfigId;
import eu.ffs.repository.ConfigRepository;
import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by j on 11.05.17.
 */
@Service
public class ConfigService {
    @Autowired
    ConfigRepository configRepository;

    public void updateInputPath(ConfigId key, String value) {
        if (!StringUtils.isEmpty(value)) {
            DashboardConfiguration dashboardConfiguration = configRepository.findOne(key);
            if (dashboardConfiguration == null) {
                dashboardConfiguration = new DashboardConfiguration(key);
            }
            dashboardConfiguration.setStringValue(value);

            configRepository.save(dashboardConfiguration);

        }
    }
}
