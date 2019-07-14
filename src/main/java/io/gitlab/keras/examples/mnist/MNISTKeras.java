package io.gitlab.keras.examples.mnist;

import io.gitlab.keras.activations.Activations;
import io.gitlab.keras.data.TensorDataset;
import io.gitlab.keras.datasets.MNISTLoader;
import io.gitlab.keras.initializers.Initializers;
import io.gitlab.keras.layers.Dense;
import io.gitlab.keras.layers.InputLayer;
import io.gitlab.keras.losses.Losses;
import io.gitlab.keras.metrics.Metrics;
import io.gitlab.keras.models.Model;
import io.gitlab.keras.models.Sequential;
import org.tensorflow.Graph;
import org.tensorflow.Shape;
import org.tensorflow.op.Ops;
import io.gitlab.keras.optimizers.Optimizers;

public class MNISTKeras {

    public static void main(String[] args) throws Exception {
        try (Graph graph = new Graph()) {
            Ops tf = Ops.create(graph);

            TensorDataset<Float> data = MNISTLoader.loadData();
            Sequential model = new Sequential(
                    new InputLayer(28 * 28, 100),
                    new Dense(10, Shape.make(100, 28 * 28))
                            .setActivation(Activations.softmax)
                            .setKernelInitializer(Initializers.zeros)
                            .setBiasInitializer(Initializers.zeros)
            );

            // Build Graph
            model.compile(tf, new Model.CompilerOptions(graph)
                    .setOptimizer(Optimizers.sgd)
                    .setLoss(Losses.softmax_crossentropy)
                    .addMetric(Metrics.accuracy));

            // Run training and evaluation
            model.fit(tf, data, 100, 100);
        }
    }

}