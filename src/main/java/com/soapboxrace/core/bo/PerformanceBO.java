/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarClassesDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.CarClassesEntity;
import com.soapboxrace.core.jpa.CarEntity;
import com.soapboxrace.core.jpa.PerformancePartEntity;
import com.soapboxrace.core.jpa.ProductEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Set;

@Stateless
public class PerformanceBO {

    @EJB
    private CarClassesDAO carClassesDAO;

    @EJB
    private ProductDAO productDAO;

    public void calcNewCarClass(CarEntity carEntity) {
        calcNewCarClass(carEntity, carEntity.getDurability() == 0);
    }

    public void calcNewCarClass(CarEntity carEntity, boolean ignoreParts) {
        int physicsProfileHash = carEntity.getPhysicsProfileHash();
        CarClassesEntity carClassesEntity = carClassesDAO.findByHash(physicsProfileHash);
        if (carClassesEntity == null) {
            return;
        }
        int topSpeed = 0;
        int accel = 0;
        int handling = 0;
        if (!ignoreParts) {
            Set<PerformancePartEntity> performanceParts = carEntity.getPerformanceParts();
            for (PerformancePartEntity performancePartEntity : performanceParts) {
                int perfHash = performancePartEntity.getPerformancePartAttribHash();
                ProductEntity productEntity = productDAO.findByHash(perfHash);
                topSpeed = productEntity.getTopSpeed() + topSpeed;
                accel = productEntity.getAccel() + accel;
                handling = productEntity.getHandling() + handling;
            }
        }
        float tt = (float) (topSpeed * 0.01);
        float ta = (float) (accel * 0.01);
        float th = (float) (handling * 0.01);
        float totalChanges = 1 / (((tt + ta + th) * 0.666666666666666f) + 1f);
        tt = tt * totalChanges;
        ta = ta * totalChanges;
        th = th * totalChanges;
        float finalConstant = 1 - tt - ta - th;

        float finalTopSpeed1 = carClassesEntity.getTsVar1().floatValue() * th;
        float finalTopSpeed2 = carClassesEntity.getTsVar2().floatValue() * ta;
        float finalTopSpeed3 = carClassesEntity.getTsVar3().floatValue() * tt;
        float finalTopSpeed =
                (finalConstant * carClassesEntity.getTsStock().floatValue()) + finalTopSpeed1 + finalTopSpeed2
                        + finalTopSpeed3;

        float finalAccel1 = carClassesEntity.getAcVar1().floatValue() * th;
        float finalAccel2 = carClassesEntity.getAcVar2().floatValue() * ta;
        float finalAccel3 = carClassesEntity.getAcVar3().floatValue() * tt;
        float finalAccel = (finalConstant * carClassesEntity.getAcStock().floatValue()) + finalAccel1 + finalAccel2
                + finalAccel3;

        float finalHandling1 = carClassesEntity.getHaVar1().floatValue() * th;
        float finalHandling2 = carClassesEntity.getHaVar2().floatValue() * ta;
        float finalHandling3 = carClassesEntity.getHaVar3().floatValue() * tt;
        float finalHandling =
                (finalConstant * carClassesEntity.getHaStock().floatValue()) + finalHandling1 + finalHandling2
                        + finalHandling3;

        float finalClass = ((int) finalTopSpeed + (int) finalAccel + (int) finalHandling) / 3f;
        int finalClassInt = (int) finalClass;

        carEntity.setCarClassHashFromClassInt(finalClassInt);
        carEntity.setRating(finalClassInt);
    }
}
