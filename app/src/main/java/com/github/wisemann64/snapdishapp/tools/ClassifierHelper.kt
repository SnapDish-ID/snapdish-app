package com.github.wisemann64.snapdishapp.tools

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ClassifierHelper private constructor(private val mApplication: Application) {

    companion object {
        private const val INPUT_MEAN = 0f
        private const val INPUT_STANDARD_DEVIATION = 255f
        private val INPUT_IMAGE_TYPE = DataType.FLOAT32
        private val OUTPUT_IMAGE_TYPE = DataType.FLOAT32
        private const val CONFIDENCE_THRESHOLD = 0.25F

        @Volatile
        private var INSTANCE: ClassifierHelper? = null

        @JvmStatic
        fun getInstance(application: Application): ClassifierHelper {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = ClassifierHelper(application)
                }
            }
            return INSTANCE as ClassifierHelper
        }
    }

    private val modelPath = "best_float32.tflite"
    private val labelPath = "labels.txt"
    private var interpreter: Interpreter
    private var tensorWidth = 0
    private var tensorHeight = 0
    private var numChannel = 0
    private var numElements = 0
    private var labels = mutableListOf<String>()

    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(640,640,ResizeOp.ResizeMethod.BILINEAR))
        .add(NormalizeOp(INPUT_MEAN, INPUT_STANDARD_DEVIATION))
        .add(CastOp(INPUT_IMAGE_TYPE))
        .build() // preprocess input

    init {
        val model = FileUtil.loadMappedFile(mApplication.applicationContext,modelPath)
        val options = Interpreter.Options()
        options.numThreads = 4
        interpreter = Interpreter(model,options)

        val inputShape = interpreter.getInputTensor(0).shape()
        val outputShape = interpreter.getOutputTensor(0).shape()

        tensorWidth = inputShape[1]
        tensorHeight = inputShape[2]
        numChannel = outputShape[1]
        numElements = outputShape[2]

        try {
            val inputStream: InputStream = mApplication.applicationContext.assets.open(labelPath)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = reader.readLine()
            while (line != null && line != "") {
                labels.add(line)
                line = reader.readLine()
            }
            reader.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun inference(uri: Uri): List<String> {
        val imageBuffer = imageProcessor.process(TensorImage.fromBitmap(uriToBitmap(uri))).buffer

        val output = TensorBuffer.createFixedSize(intArrayOf(1 , numChannel, numElements), OUTPUT_IMAGE_TYPE)
        interpreter.run(imageBuffer, output.buffer)

        val floatArray = output.floatArray

        val resultArray = Array(8400) { FloatArray(36) }

        var index = 0
        for (i in 0 until 36) {
            for (j in 0 until 8400) {
                resultArray[j][i] = floatArray[index]
                index++
            }
        }

        val newResultArray = Array(8400) { FloatArray(32) }

        for (i in 0 until 8400) {
            for (j in 0 until 32) {
                newResultArray[i][j] = resultArray[i][j+4]
            }
        }

        val unique: HashSet<String> = HashSet()

        for (i in 0..<8400) {
            val ma = maxArgmax(newResultArray[i])
            if (ma.second > CONFIDENCE_THRESHOLD) {
                unique.add(labels[ma.first])
            }
        }

        return unique.toList()
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = mApplication.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream).also {
                inputStream?.close()
            }
        } catch (e: Exception) {
            Log.e("ImageClassifierHelper",e.message.toString())
            null
        }
    }

    private fun maxArgmax(arr: FloatArray): Pair<Int,Float> {
        var idx = 0
        var max = arr[0]

        for (i in 1..<arr.size) {
            if (arr[i] > max) {
                idx = i
                max = arr[i]
            }
        }
        return Pair(idx,max)
    }
}