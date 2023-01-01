package optifine;

import java.lang.reflect.Field;

public class FieldLocatorFixed implements IFieldLocator
{
    private Field field;

    public FieldLocatorFixed(Field p_i37_1_)
    {
        field = p_i37_1_;
    }

    public Field getField()
    {
        return field;
    }
}