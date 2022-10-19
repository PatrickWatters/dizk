package profiler.profiling;

import algebra.fft.DistributedFFT;
import algebra.fft.SerialFFT;
import algebra.curves.barreto_naehrig.bn254a.BN254aFields.BN254aFr;
import common.MathUtils;
import configuration.Configuration;
import org.apache.spark.api.java.JavaPairRDD;
import profiler.generation.FFTGenerator;
import java.math.BigInteger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FFTProfiling {


    public static void test_fft_with_input()
    {   
        try
        {
            final BN254aFr fieldFactory = new BN254aFr(2L);
            final ArrayList<BN254aFr> serial = new ArrayList<>();

            //BigInteger omegai = new BigInteger("09c532c6306b93d29678200d47c0b2a99c18d51b838eeb1d3eed4c533bb512d0",16);
            //BN254aFr omega = new BN254aFr(omegai);

            //System.out.println(omega);
            //BigInteger firstoutput = new BigInteger("0553ef15066dc22fe2f8c8fd544389b711e419998e00ae37c626f5d7a81be516",16);
            //System.out.println(firstoutput);

            JSONParser parser = new JSONParser();      
            Object obj = parser.parse(new FileReader("/Users/patrickwatters/Projects/dizk/ifft_before.json"));
            JSONArray a = (JSONArray)obj;
            for (Object o : a)
            {
                String line = o.toString();
                BigInteger i = new BigInteger(line,16);
                //System.out.println(i);
                BN254aFr frt = new BN254aFr(i);
                serial.add(frt);
            }

            final SerialFFT<BN254aFr> domain = new SerialFFT<>(a.size(), fieldFactory);
            domain.radix2FFT(serial);
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public static void serialFFTProfiling(final Configuration config, final long size) {
        final BN254aFr fieldFactory = new BN254aFr(2L);

        final ArrayList<BN254aFr> serial = new ArrayList<>();
        for (int j = 0; j < size; j++) {

            serial.add(fieldFactory.random(config.seed(), config.secureSeed()));
        }

        final SerialFFT<BN254aFr> domain = new SerialFFT<>(size, fieldFactory);

        config.setContext("FFTProfiling-Serial");
        config.beginRuntimeMetadata("Size (inputs)", size);

        config.beginLog("FFT");
        config.beginRuntime("FFT");
        domain.radix2FFT(serial);
        config.endRuntime("FFT");
        config.endLog("FFT");

        config.writeRuntimeLog(config.context());
    }

    public static void distributedFFTProfiling(final Configuration config, final long size) {
        final BN254aFr fieldFactory = new BN254aFr(2L);
        final JavaPairRDD<Long, BN254aFr> distributed = FFTGenerator.generateData(config, size);

        final long k = MathUtils.lowestPowerOfTwo((long) Math.sqrt(size));
        final long rows = size / k;
        final long cols = k;

        config.setContext("FFT");
        config.beginRuntimeMetadata("Size (inputs)", size);
        config.beginRuntimeMetadata("Column Size (inputs)", k);

        config.beginLog("FFT");
        config.beginRuntime("FFT");
        DistributedFFT.radix2FFT(distributed, rows, cols, fieldFactory).count();
        config.endRuntime("FFT");
        config.endLog("FFT");

        config.writeRuntimeLog(config.context());
    }
    
}
