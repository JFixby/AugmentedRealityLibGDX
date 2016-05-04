
package com.blogspot.mesai0;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import cameracontrol.AndroidDeviceCameraController;
import combinedview.CameraActivity;
import libgdx.Renderer;

public class MainActivity extends CameraActivity {

	private AndroidDeviceCameraController cameraControl;
	private Renderer renderer;
	private View view;

	@Override
	public void onCreate (final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		final AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.r = 8;
		cfg.g = 8;
		cfg.b = 8;
		cfg.a = 8;

		// Camera Part

		this.cameraControl = new AndroidDeviceCameraController(this);

		this.view = this.initializeForView(this.renderer = new Renderer(this, this.cameraControl), cfg);
		// keep the original screen size
		this.origWidth = this.graphics.getWidth();
		this.origHeight = this.graphics.getHeight();

		if (this.graphics.getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics.getView();
			// force alpha channel - I'm not sure we need this as the GL surface
			// is already using alpha channel
			glView.getHolder().setFormat(PixelFormat.RGBA_8888);
		}
		// we don't want the screen to turn off during the long image saving
		// process

		this.graphics.getView().setKeepScreenOn(true);

		this.main = (FrameLayout)this.findViewById(R.id.main_layout);
		this.main.addView(this.view, 0);
	}

	@Override
	protected void onDestroy () {
		this.renderer.dispose();
		super.onDestroy();
	}

}
