(ns random)

(import java.util.Random)

(defn with-seed [#^long seed]
  (Random. seed))

(defn without-seed []
  (Random.))

(defn randomInt [#^Random random min max]
  "Generate a random integer number between min and max, inclusive."
  (+ (.nextInt random (+ (- max min) 1)) min))
