package org.ksoap2.serialization;

public interface HasAttributes {
    int getAttributeCount();

    void getAttributeInfo(int i, AttributeInfo attributeInfo);
}
