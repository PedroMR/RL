(ns rl.map
  (:require (rl random))
  (:use clojure.test))

(defprotocol MapView
  (in-bounds? [this x y])
  (walkable? [this x y]))
(defprotocol MapEdit
  (with-wall-at [this x y]))
(deftype Map
  [width height]
  MapView
  (in-bounds? [this x y] (and
                           (< y height)
                           (>= y 0)
                           (>= x 0)
                           (< x width)))
  (walkable? [this x y] (in-bounds? this x y))
  MapEdit
  (with-wall-at [this x y] this))

(let [map (with-wall-at (Map. 50 50) 20 20)]
  (deftest testBounds
    (is (in-bounds? map 30 30))
    (is (not (in-bounds? map 50 30)))
    (is (not (in-bounds? map 10 50)))
    (is (not (in-bounds? map -1 30)))
    (is (not (in-bounds? map 30 -1)))
    )
  (deftest testWalkable
    (is (walkable? map 10 10)))
    (is (not (walkable? map 100 10)))
    (is (not (walkable? map 20 20)))
  )

