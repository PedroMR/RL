(ns rl
  (:import
    (javax.swing JFrame)
    (java.awt TextArea Font Color Toolkit)
    (java.awt.event ActionListener KeyListener KeyEvent))
  )

(println "Hello!" )

(defn handle-input [#^KeyEvent event]
  (println (.getKeyCode event) (.getKeyChar event)))

(defn render-row-as-string [mapView row cols]
  (loop [line ""
         col 0
         cols cols]
    (if (> cols 0)
      (recur (str line ".") (inc col) (dec cols))
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

(defn map-view [width height]

  )

(defn input-listener []
  (proxy [ActionListener KeyListener] []
    (actionPerformed [e])
    (keyPressed [e] (handle-input e))
    (keyReleased [e])
    (keyTyped [e])))

(let [frame (JFrame. "RL")
      textArea (TextArea. "Hubba!")
      view-rows 30
      view-cols 24]
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

  (display-grid textArea (map-view 100 100))

  )