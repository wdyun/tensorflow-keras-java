package io.gitlab.keras.losses;

import io.gitlab.keras.metrics.Metric;
import org.tensorflow.Operand;
import org.tensorflow.Shape;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;

public class MeanSquaredError extends Loss {
    @Override
    protected Operand<Float> call(Ops tf, Operand<Float> actual, Placeholder<Float> labels) {
        return tf.math.mean(tf.math.squaredDifference(actual, labels), tf.constant(-1));
    }

    @Override
    public void build(Ops tf, Shape inputShape) {

    }

    @Override
    public Shape computeOutputShape(Shape inputShape) {
        // TODO: multiple shapes as input
        return Shape.unknown();
    }
}