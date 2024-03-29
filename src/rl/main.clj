(ns rl.main
  (:import
    (javax.swing JFrame)
    (java.awt TextArea Font Color)
    (java.awt.event ActionListener KeyListener KeyEvent))
  (:require
    (rl random))
  (:use
    clojure.test
    rl.map))

(defstruct tile :position :wall)

(defn handle-input [#^KeyEvent event]
  (println (.getKeyCode event) (.getKeyChar event)))

(defn get-char-for-pos [mapView row col]
  (if-not (.walkable? mapView col row)
    "#"
    "."))

(defn render-row-as-string [mapView row cols]
  (loop [line ""
         col 0
         cols cols]
    (if (> cols 0)
      (recur (str line (get-char-for-pos mapView row col)) (inc col) (dec cols))
      (str line "\n"))))

(defn render-map-as-string [mapView rows cols]
  (loop [text ""
         row 0
         rows rows]
    (if (> rows 0)
      (recur (str text (render-row-as-string mapView row cols)) (inc row) (dec rows))
      text)))

(defn display-grid [#^TextArea textArea #^rl.map.MapView mapView]
  (.setText textArea (.toString (render-map-as-string mapView (.getRows textArea) (.getColumns textArea)))))
;  (let [view ""]
;    (dotimes [y (.getRows textArea)]
;      (dotimes [x (.getColumns textArea)]
;        (.appendText textArea "."))
;      (.appendText textArea "\n"))
;    (.setText textArea view)))

(defn map-view [seed width height]
  (fn [row col]
    ;(str (rem (+ row col) 10))

    ))

(defn input-listener []
  (proxy [ActionListener KeyListener] []
    (actionPerformed [e])
    (keyPressed [e] (handle-input e))
    (keyReleased [e])
    (keyTyped [e])))

(defn create-map [seed]
  (.randomize-rooms (rl.map.Map. 100 50) seed))

(defn main []
  (let [frame (JFrame. "RL")
        textArea (TextArea. "Hubba!")
        view-rows 80
        view-cols 24
        seed 4098
        map (create-map seed)
        ]
    (doto frame
      (.setSize 640 480)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setResizable false)
      (.add textArea)
      (.addKeyListener (input-listener))
      (.requestFocus)
      (.setVisible true))
    (doto textArea
      (.setFont (Font. "Monospaced" (Font/PLAIN) 11))
      (.setColumns view-rows)
      (.setRows view-cols)
      (.setFocusable false)
      (.setForeground (Color/GREEN))
      (.setBackground (Color/BLACK))
      )

    (display-grid textArea map)

    ))


(defn print-help []
  (println "Use 'play' as a command line argument to run the game."))

(rl.random/without-seed)

(if (some #{"play"} *command-line-args*)
  (main)
  (print-help))
