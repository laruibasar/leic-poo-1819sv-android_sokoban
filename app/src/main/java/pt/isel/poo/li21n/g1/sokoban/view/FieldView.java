package pt.isel.poo.li21n.g1.sokoban.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.isel.poo.li21n.g1.sokoban.R;

public class FieldView extends LinearLayout {
    private TextView label;
    private TextView value;

    public FieldView(Context context) {
        super(context);
        init(context);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        final TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.FieldView);
        try {
            final String labelText = attrsArray.getString(R.styleable.FieldView_label);
            if (labelText != null) {
                setLabel(labelText);
            }

            final int initialValue = attrsArray.getInt(R.styleable.FieldView_value, 0);
            setValue(initialValue);
        } finally {
            attrsArray.recycle();
        }
    }

    private void init(Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        li.inflate(R.layout.field_view, this);
        label = findViewById(R.id.label);
        value = findViewById(R.id.value);
    }

    public void setLabel(String label) {
        this.label.setText(label);
        invalidate();
    }

    public void setValue(int value) {
        this.value.setText(Integer.toString(value));
        invalidate();
    }
}
