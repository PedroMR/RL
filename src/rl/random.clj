(ns rl.random
  (:use clojure.test))

(import java.util.Random)

(defn with-seed [#^long seed]
  (Random. seed))

(defn without-seed []
  (Random.))

(with-test
  (defn randomInt [#^Random random min max]
    "Generate a random integer number between min and max, inclusive."
    (+ (.nextInt random (+ (- max min) 1)) min))
  (is (= 15 (randomInt (with-seed 0xCAFE) 0 100)))
  (is (every? (fn [x] (<= x 5))
  (take 10
    (let [rand (with-seed 0xCAFE)]
      (repeatedly
        #(randomInt rand 0 5))))))
)

(prn
  (take 10
    (let [rand (with-seed 0xCAFE)]
      (repeatedly
        #(randomInt rand 0 100)))))
