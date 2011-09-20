(ns rl.main
  (:import
    (javax.swing JFrame)
    (java.awt TextArea Font Color)
    (java.awt.event ActionListener KeyListener KeyEvent))
  (:require
    (rl random map))
  (:use clojure.test))

(defstruct position :x :y)
(defstruct tile :position :wall)

(defn handle-input [#^KeyEvent event]
  (println (.getKeyCode event) (.getKeyChar event)))

(defn render-row-as-string [mapView row cols]
  (loop [line ""
         col 0
         cols cols]
    (if (> cols 0)
      (recur (str line (mapView row col)) (inc col) (dec cols))
      (str line "\n"))))

(defn render-map-as-string [mapView rows cols]
  (loop [text ""
         row 0
         rows rows]
    (if (> rows 0)
      (recur (str text (render-row-as-string mapView row cols)) (inc row) (dec rows))
      text)))

(defn display-grid [#^TextArea textArea mapView]
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

(defn main []
  (let [frame (JFrame. "RL")
        textArea (TextArea. "Hubba!")
        view-rows 80
        view-cols 24
        seed 4098]
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

    (display-grid textArea (map-view seed 100 100))

    ))


(defn print-help []
  (println "Use 'play' as a command line argument to run the game."))

(rl.random/without-seed)

(if (some #{"play"} *command-line-args*)
  (main)
  (print-help))
