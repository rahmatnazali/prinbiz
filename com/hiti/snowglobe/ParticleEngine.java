package com.hiti.snowglobe;

import java.util.ArrayList;

public class ParticleEngine {
    long m_CurrentTime;
    ParticleView m_ParticleView;
    double m_Span;
    double m_Time;

    ParticleEngine(ParticleView particleView) {
        this.m_Time = 0.0d;
        this.m_Span = 30000.0d;
        this.m_CurrentTime = -1;
        this.m_ParticleView = particleView;
    }

    void Add(double dTime) {
        int iBmpIndex = this.m_ParticleView.m_ParticlePhoto.GetRandomIndex();
        this.m_ParticleView.m_ParticleGroup.Add(iBmpIndex, this.m_ParticleView.m_ParticlePhoto.GetPhotoType(iBmpIndex), dTime, this.m_ParticleView.getWidth(), this.m_ParticleView.getHeight());
    }

    void Move_Method_1() {
        if (System.currentTimeMillis() - this.m_CurrentTime > 70 || this.m_CurrentTime == -1) {
            Add(this.m_Time);
            this.m_CurrentTime = System.currentTimeMillis();
        }
        ArrayList<Particle> tempSet = this.m_ParticleView.m_ParticleGroup.GetParticleArrayList();
        int count = tempSet.size();
        for (int i = 0; i < count; i++) {
            Particle particle = (Particle) tempSet.get(i);
            double timeSpan = this.m_Time - particle.m_dStartTime;
            float tempx = (float) (((double) particle.m_fStartX) + (particle.m_dHorizontalSpeed * timeSpan));
            float tempy = (float) ((((double) particle.m_fStartY) + (particle.m_dVerticalSpeed * timeSpan)) + ((0.0d * timeSpan) * timeSpan));
            if (tempy > ((float) this.m_ParticleView.getHeight()) / particle.m_fScale) {
                tempSet.remove(particle);
                count = tempSet.size();
            }
            particle.m_fCurrentX = tempx;
            particle.m_fCurrentY = tempy;
        }
        this.m_Time += ((double) this.m_ParticleView.getHeight()) / this.m_Span;
    }
}
