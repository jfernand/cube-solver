//import javafx.application.Application.launch
//import javafx.scene.Scene
//import javafx.scene.layout.BorderPane
//import javafx.stage.Stage
//import org.opencv.core.Core
//import org.opencv.core.Core.KMEANS_PP_CENTERS
//import org.opencv.core.Core.kmeans
//import org.opencv.core.Mat
//import org.opencv.core.MatOfPoint2f
//import org.opencv.core.TermCriteria
//import org.opencv.highgui.HighGui
//import org.opencv.imgproc.Imgproc.COLOR_BGR2HLS
//import org.opencv.imgproc.Imgproc.cvtColor
//import org.opencv.videoio.VideoCapture
//
//fun main(args: Array<String>) {
//    println("Welcome to OpenCV " + Core.VERSION)
//    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//    launch(ColorDetector::class.java, *args)
//}
//
//class ColorDetector : javafx.application.Application() {
//    var pane = BorderPane()
//    override fun start(primaryStage: Stage) {
//        val loader = FXMLLoader(javaClass.getResource("FXHelloCV.fxml"))
//        val root = loader.load()
//        val controller: FXController = loader.getController()
//        val scene = Scene(pane, 1024.0, 768.0, true)
//        scene.stylesheets.add(javaClass.getResource("application.css").toExternalForm())
//        primaryStage.title = "ColorDetector"
//        primaryStage.scene = scene
//        primaryStage.show()
//
//        val cap = VideoCapture(1)
//        val frame = Mat()
//        val floatFrame = MatOfPoint2f()
//        val clusteredFrame = Mat()
//        val hsv = Mat()
//        cap.grab()
//        val hierachy = Mat()
//        while (true) {
//            cap.retrieve(frame)
//            println("frame dimensions ${frame.dims()} type ${frame.t()}")
//
//            cvtColor(frame, hsv, COLOR_BGR2HLS)
//            frame.assignTo(floatFrame, 5)
//            val points = floatFrame.reshape(3, 1)
//            println("points dimensions ${points.dims()} type ${points.t()}")
//            val labels = Mat()
//            val centers = Mat()
//            val clusterCount = 15
//            val compactness: Double = kmeans(
//                points,
//                clusterCount,
//                labels,
//                TermCriteria(TermCriteria.EPS + TermCriteria.COUNT, 10, 1.0),
//                3, KMEANS_PP_CENTERS, centers
//            )
//            println("labels dimensions ${labels.dims()} type ${labels.t()}")
//            println("centers dimensions ${centers.dims()} type ${centers.t()}")
//            println(centers.dump())
//            println(compactness)
//            val newFrame = labels.reshape(1, 720)
//            println("newFrame dimensions ${newFrame.dims()} type ${newFrame.t()}")
//            for (row in 0 until newFrame.rows()) {
//                for (column in 0 until newFrame.cols()) {
//                    val label = newFrame.get(row, column)[0].toInt() * 3
//                    println(label)
////                clusteredFrame.set(row, column,   labels.get(newFrame.get(row,column)])
//                }
//            }
//            HighGui.imshow("Edges", frame)
//            HighGui.waitKey()
//        }
//    }
//}
//
