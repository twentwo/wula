package io.yec.wula.example.extpoint;

import io.yec.wula.core.extension.annotation.ExtPoint;
import io.yec.wula.example.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * YellowRouter
 *
 * @author baijiu.yec
 * @since 2022/07/07
 */
@Slf4j
@ExtPoint("yellowRouter")
public class YellowRouter implements IPersonRouter {
    @Override
    public String desc(PersonEntity personEntity) {
        return personEntity.toString();
    }
}
