package io.yec.wula.example.extpoint;

import io.yec.wula.core.extension.ExtensionPoint;
import io.yec.wula.example.entity.PersonEntity;

/**
 * IPersonRouter
 *
 * @author baijiu.yec
 * @since 2022/07/07
 */
public interface IPersonRouter extends ExtensionPoint {
    String desc(PersonEntity personEntity);
}
