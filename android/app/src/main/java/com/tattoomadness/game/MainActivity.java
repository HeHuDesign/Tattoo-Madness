package com.tattoomadness.game;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.activity.OnBackPressedCallback;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Don't let the screen dim or sleep in the middle of a tattoo.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hideSystemBars();

        // No pinch-to-zoom on the whole app (the city map does its own pinch zoom in the game).
        WebView wv = getBridge() != null ? getBridge().getWebView() : null;
        if (wv != null) {
            wv.getSettings().setSupportZoom(false);
            wv.getSettings().setBuiltInZoomControls(false);
            wv.getSettings().setDisplayZoomControls(false);
        }

        // Back button / back gesture (and a controller's B button, which Android turns into "back")
        // goes to the game first. The game returns true when it handled it (closing a menu, pausing, etc.).
        // Only on the title screen does back send the app to the background.
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                WebView webView = getBridge() != null ? getBridge().getWebView() : null;
                if (webView == null) {
                    moveTaskToBack(true);
                    return;
                }
                webView.evaluateJavascript("(window.__tmBack ? window.__tmBack() : false)", result -> {
                    if (!"true".equals(result)) moveTaskToBack(true);
                });
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemBars(); // re-hide after notifications, the recents screen, etc.
    }

    // Full-screen "immersive" mode: status and navigation bars hide; swipe from the edge to peek at them.
    private void hideSystemBars() {
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}
