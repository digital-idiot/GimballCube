/**
 * Created by abhisek on 02/28/17.
 */

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.jogamp.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static com.jogamp.opengl.GL.GL_SRC_ALPHA;

public class Cube implements GLEventListener {

	private GLU glu = new GLU();
	private float rquad = 0.0f;

	@Override
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
		gl.glLoadIdentity();
		gl.glTranslatef( 0f, 0f, -7f );

		// Rotate The Cube On X, Y & Z i.e. pass quaternion as argument
		gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f);

		//giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f(1f,0f,0f); //red color
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)

		gl.glColor3f( 0f,1f,0f ); //green color
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad

		gl.glColor3f( 0f,0f,1f ); //blue color
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad

		gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)

		gl.glColor3f( 1f,0f,1f ); //purple (red + green)
		gl.glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
		gl.glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
		gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
		gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad

		gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
		gl.glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
		gl.glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad
		gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
		gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();

		rquad = ((rquad + 1.0f) % 360);
	}

	@Override
	public void dispose( GLAutoDrawable drawable ) {}

	@Override
	public void init( GLAutoDrawable drawable ) {

		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel( GL2.GL_SMOOTH );
		gl.glClearColor( 0f, 0f, 0f, 0f );
		gl.glClearDepth( 1.0f );
		gl.glEnable( GL2.GL_DEPTH_TEST );
		gl.glDepthFunc( GL2.GL_LEQUAL );
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
		gl.glHint( GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST );
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	}

	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {

		final GL2 gl = drawable.getGL().getGL2();
		if( height <= 0 )
		height = 1;

		final float h = ( float ) width / ( float ) height;
		gl.glViewport( 0, 0, width, height );
		gl.glMatrixMode( GL2.GL_PROJECTION );
		gl.glLoadIdentity();

		glu.gluPerspective( 45.0f, h, 1.0, 30.0 );
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
	}

	public static void main( String[] args ) {

		final GLProfile profile = GLProfile.get( GLProfile.GL2 );
		GLCapabilities capabilities = new GLCapabilities( profile );

		// The canvas
		final GLCanvas glcanvas = new GLCanvas( capabilities );
		Cube cube = new Cube();

		glcanvas.addGLEventListener( cube );
		glcanvas.setSize( 512, 512 );

		final JFrame frame = new JFrame ( " Multicolored Cube" );
		frame.getContentPane().add( glcanvas );
		frame.setSize( frame.getContentPane().getPreferredSize() );
		frame.setVisible( true );
		final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				animator.stop();
				frame.dispose();
				System.exit(0);
			}
		});
		animator.start();
	}

}