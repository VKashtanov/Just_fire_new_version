package ru.kashtanov.just_fire_service.service;

import ru.kashtanov.just_fire_service.model.SiteConfig;
import ru.kashtanov.just_fire_service.model.User;

/**
 * @author Viktor Кashtanov
 */
public interface SiteConfigService {
    public SiteConfig getBySiteId(int id);

}
