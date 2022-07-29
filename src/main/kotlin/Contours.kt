//
//import org.opencv.core.Core
//import org.opencv.core.Mat
//import org.opencv.core.MatOfPoint
//import org.opencv.core.MatOfPoint2f
//import org.opencv.core.Size
//import org.opencv.highgui.HighGui
//import org.opencv.imgproc.Imgproc.*
//import org.opencv.videoio.VideoCapture
//
//object Main {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        println("Welcome to OpenCV " + Core.VERSION)
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//        val cap = VideoCapture(1)
////        namedWindow( "Histogram", 0 );
////        namedWindow( "CamShift Demo", 0 );
//        val frame = Mat()
//        val gray = Mat()
//        val thresholed = Mat()
//        val blurred = Mat()
//        cap.grab()
//        val hierachy = Mat()
//        while (true) {
//            cap.retrieve(frame)
//            GaussianBlur(frame, blurred,  Size(5.0,5.0), 1.5); //GaussianBlur to reduce noise
//
//            cvtColor(blurred, gray,COLOR_BGR2GRAY)
//            adaptiveThreshold(gray, thresholed, 100.0, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY,101, 10.0)
//            val contours: List<MatOfPoint> = mutableListOf()
//            findContours(
//                thresholed,
//                contours,
//                hierachy,
//                RETR_LIST,
//                CHAIN_APPROX_TC89_KCOS
//            ); //Find contours
////            drawContours(frame, contours, -1, Scalar(0.0, 255.0, 0.0, .5), FILLED)
//            contours.forEachIndexed { i, contour ->
//                if (contourArea(contour) > 200) {
//                    val approx = MatOfPoint2f()
//                    val c = MatOfPoint2f()
//                    c.fromList(contour.toArray().toList())
//                    approxPolyDP(c, approx, arcLength(c, true)*0.5, true)
//
////                    drawContours(frame, mutableListOf(approx), i, Scalar(i*10.0, i*10.0, i*10.0, 200.0), 1)
//                }
//            }
////            for (contour in contours) {
////
////                val area = contourArea(contour)
////                contour.toArray().forEach { circle(frame, it, 5, Scalar(255.0,0.0,0.0)) }
////                if (area > 1000) {
////                    val shape = MatOfPoint2f()
////                    shape.fromList(contour.toArray().asList())
////                    val rect = minAreaRect(shape)
////                    val radius = kotlin.math.sqrt(area / 3.1415).toInt()
////                    circle(frame, rect.center, radius, Scalar(1.0, 1.0, 255.0))
////                }
////            }
//            HighGui.imshow("Edges", thresholed)
//            HighGui.imshow("Grabby", frame)
//            HighGui.waitKey(100)
////            HighGui.waitKey()
//            cap.grab()
//        }
//    }
//}
//
////Mat input = new Mat(); //The image
////Mat blur = new Mat();
////Mat canny = new Mat();
////
////
////Imgproc.Canny(blur, canny, 60, 70); //Canny to detect the edges
////Imgproc.GaussianBlur(canny, canny, new Size(3,3), 1.5); //Again GaussianBlur to reduce noise
////
////List<MatOfPoint> contours = new ArrayList<>();
////Mat hierachy = new Mat();
////
////Imgproc.findContours(canny, contours, hierachy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE); //Find contours
////
////List<MatOfPoint2f> approxedShapes = new ArrayList<>();
////for(MatOfPoint point : contours){
////    double area = Imgproc.contourArea(point);
////    if(area > 1000){
////        MatOfPoint2f shape = new MatOfPoint2f(point.toArray());
////        MatOfPoint2f approxedShape = new MatOfPoint2f();
////
////        double epsilon = Imgproc.arcLength(shape, true) / 10;
////
////        Imgproc.approxPolyDP(shape, approxedShape, epsilon, true); //"Smooth" the edges with approxPolyDP
////        approxedShapes.add(approxedShape);
////    }
////}
////
//////Visualisation
////for(MatOfPoint2f point : approxedShapes){
////    RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(point.toArray()));
////    Imgproc.circle(input, rect.center, 5, new Scalar(0, 0, 255));
////
////    for(Point p : point.toArray()){
////        Imgproc.circle(input, p, 5, new Scalar(0,255,0));
////    }
////}
