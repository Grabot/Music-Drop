package com.Android.games.Music_Drop.Load;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;

public class Spectrum_Analyzer
{

	public static final String LOG = Spectrum_Analyzer.class.getSimpleName();
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	String FILE = null;
	Mpg123Decoder decoder;
	AudioDevice device;
	boolean playing = false;
	
	//private FileHandle externalFile;
	private FileHandle chosen_file;
	
	private Thread playbackThread;
	short[] samples = new short[2048];

	KissFFT fft;
	float[] spectrum = new float[2048];
	float[] maxValues = new float[2048];
	float[] topValues = new float[2048];
	
	private float song_length = 0;
	
	private String location = null;
	
	private boolean pause = false;
	
	public Spectrum_Analyzer( String file_path )
	{

		if (Gdx.app.getType() == ApplicationType.Android) 
		{
				location = file_path;
				//location = "/external_sd/music/overig/Wait_Until_Tomorrow-John_Mayer.mp3";
				chosen_file = Gdx.files.external(location);
		} 
		else 
		{
				FILE = "music/crescendo.mp3";
				chosen_file = Gdx.files.external("tmp/audio-spectrum.mp3");
				Gdx.files.internal(FILE).copyTo(chosen_file);
		}
		
		// fast fourier transform
		fft = new KissFFT(2048);

		for (int i = 0; i < maxValues.length; i++) 
		{
			maxValues[i] = 0;
			topValues[i] = 0;
		}

		// create the decoder
		decoder = new Mpg123Decoder(chosen_file);

		song_length = decoder.getLength();
		//the_song = decoder.readAllSamples();
		Gdx.app.log( Spectrum_Analyzer.LOG, "song length: " + decoder.getLength() );
		Gdx.app.log( Spectrum_Analyzer.LOG, "song rate: " + decoder.getRate() );

		// Create an audio device for playback
		device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);

		// start a thread for playback
		playbackThread = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				int readSamples = 0;

				// read until we reach the end of the file
				while ( playing ) 
				{
					if( !pause )
					{
						if(( readSamples = decoder.readSamples(samples, 0, samples.length)) > 0 )
						{
							// get audio spectrum
							fft.spectrum(samples, spectrum);
							// write the samples to the AudioDevice
							device.writeSamples(samples, 0, readSamples);
						}
					}
				}
			}
		});
		
		playbackThread.setDaemon(true);
		playbackThread.start();
		playing = true;
	}
	
	public float get_Bass_Spectrum()
	{
		float bass = 0;
		
		for( int i = 0; i < 70; i++ )
		{
			bass = bass + spectrum[i];
		}
		
		return bass;
	}
	
	public float get_Mid_Spectrum()
	{
		float mid = 0;
		
		for( int i = 71; i < 1500; i++ )
		{
			mid = mid + spectrum[i];
		}
		
		return mid;
	}
	
	public float get_song_length()
	{
		return song_length;
	}
	
	public void set_pause( boolean pause )
	{
		this.pause = pause;
	}
	
	public void stop_playing()
	{
		playing = false;
	}

}
