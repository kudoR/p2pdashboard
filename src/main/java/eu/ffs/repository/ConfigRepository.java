package eu.ffs.repository;

import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by j on 05.05.17.
 */
public interface ConfigRepository extends CrudRepository<DashboardConfiguration, ConfigId> {
}
