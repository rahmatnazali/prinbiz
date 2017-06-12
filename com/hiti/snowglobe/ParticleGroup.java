package com.hiti.snowglobe;

import java.util.ArrayList;

public class ParticleGroup {
    private ArrayList<Particle> m_ParticleArrayList;

    public ParticleGroup() {
        this.m_ParticleArrayList = new ArrayList();
    }

    public boolean HaveParticle() {
        if (this.m_ParticleArrayList.size() > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Particle> GetParticleArrayList() {
        return this.m_ParticleArrayList;
    }

    public void ClearParticleArrayList() {
        this.m_ParticleArrayList.clear();
    }

    public void Add(int iBmpIndex, int iType, double startTime, int iScreenWidth, int iScreenHeight) {
        int tempBmpIndex = iBmpIndex;
        int tempType = iType;
        float tempScale = (float) Math.random();
        if (((double) tempScale) < 0.5d) {
            tempScale += 0.5f;
        }
        int iAlpha = (int) (Math.random() * 256.0d);
        if (iAlpha < 80) {
            iAlpha += 80;
        }
        this.m_ParticleArrayList.add(new Particle(tempBmpIndex, tempType, tempScale, iAlpha, 200.0d + (100.0d * Math.random()), 0.0d, (float) ((int) (((double) iScreenWidth) * Math.random())), (float) 0, startTime));
    }
}
