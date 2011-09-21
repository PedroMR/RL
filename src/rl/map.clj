(ns rl.map
  (:require (rl random))
  (:use clojure.test))

(defprotocol MapView
  (in-bounds? [this x y])
  (walkable? [this x y]))
(defprotocol MapEdit
  (with-wall-at [this x y])
  (add-position-to-set [this set-index position]))

;(defstruct map-struct :width :height :walls)
;
;(defn new-map [width height]
;  (create-struct width height {}))

(defrecord Map
  [width height]
  MapView
  (in-bounds? [this x y] (and
                           (< y height)
                           (>= y 0)
                           (>= x 0)
                           (< x width)))
  (walkable? [this x y] (and
                          (in-bounds? this x y)
                          (nil?
                            (get
                              (get this :walls)
                              [x y]))))
  MapEdit
  (with-wall-at [this x y]
    (add-position-to-set this :walls [x y]))
  (add-position-to-set [this set-index position]
    (let [new-map (assoc
      this
      set-index
      (conj
        (or (get this set-index) #{})
        position)
    )]
      (prn new-map)
      new-map))
  )
(let [map (with-wall-at (with-wall-at (Map. 50 50) 20 20) 21 20)]
  (deftest testBounds
    (is (in-bounds? map 30 30))
    (is (not (in-bounds? map 50 30)))
    (is (not (in-bounds? map 10 50)))
    (is (not (in-bounds? map -1 30)))
    (is (not (in-bounds? map 30 -1)))
    )
  (deftest testWalkable
    (is (walkable? map 10 10))
    (is (walkable? map 21 21))
    (is (not (walkable? map 100 10)))
    (is (not (walkable? map 20 20)))
  )
)

