(ns rl
  (:import
    (javax.swing JFrame)
    (java.awt TextArea Font Color Toolkit)
    (java.awt.event ActionListener KeyListener KeyEvent))
  )

(println "Hello!" )

(defn input-listener []
  (proxy [ActionListener KeyListener] []
    (actionPerformed [e])
    (keyPressed [e] (println e)) ;handle-input
    (keyReleased [e])
    (keyTyped [e])))


(let [frame (JFrame. "RL")
      textArea (TextArea. "Hubba!")]
  (doto frame
    (.setSize 640 480)
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
    (.setResizable false)
    (.add textArea)
    (.setVisible true))
  (doto textArea
    (.setFont (Font. "Monospaced" (Font/PLAIN) 11))
    (.setColumns 80)
    (.setRows 24)
    (.addKeyListener (input-listener))
    (.setForeground (Color/GREEN))
    (.setBackground (Color/BLACK))
    ))