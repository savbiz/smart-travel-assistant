package expertsystem;

import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import jess.JessException;
import jess.Main;
import jess.Rete;
import jess.awt.TextReader;

public class Logic implements Iterable<Question>, Iterator<Question> {
	int count = -1;

	boolean logicComplete = false;
	private Rete engine;
	private Main mene;
	private Queue<Question> que;
	private Queue<String> aque;
	private StringWriter out;
	private PrintWriter pw;

	private Question memory = null;
	// private BufferedReader input;
	// private PipedOutputStream input;
	private TextReader in;
	private String LastOutput = "";

	public Logic(File f) {

		Log.e("JESS.Smart Travel Assistant", "Logic init");
		String s = "6"; // test
		que = new LinkedList<Question>();
		aque = new LinkedList<String>();
		in = new TextReader(true);
		engine = new Rete();

		engine.addInputRouter("in", in, false);
		Log.e("JESS.Smart Travel Assistant", "Logic Rete made");
		String[] argv = new String[0];
		try {
			mene = new Main();
			out = new StringWriter();
			pw = new PrintWriter(out);
			mene.initialize(argv, engine);
			engine.addOutputRouter("out", pw);

			Log.e("JESS.Smart Travel Assistant", "Logic out made");

			engine.executeCommand("(batch " + f.toString() + ")");

			Log.e("JESS.Smart Travel Assistant", "Logic batch sucsessful");
			engine.reset();
			Log.e("JESS.Smart Travel Assistant", "Logic reset done");

			Log.e("JESS.Smart Travel Assistant", "Logic init done");

		} catch (Exception E) {
			s = "8"; // test
		}
	}

	// constructors and other stuff here

	public Iterator<Question> iterator() {
		return this;
	}

	public boolean hasNext() {
		Log.e("JESS.Smart Travel Assistant", "hasNext()");
		return !logicComplete;
	}

	public Question logicOp(String result) throws JessException {
		Log.e("JESS.Smart Travel Assistant", "RESTART******LogicOp:");

		String s = "";
		if (result == "") {

			Log.e("JESS.Smart Travel Assistant", "Result: null");
			return memory;

		} else {
			engine.executeCommand("(agenda)");
			Log.e("JESS.Smart Travel Assistant", "Result: " + result);

			if (!result.equals("Start")) {
				try {
					Log.e("result", "que.size():" + que.size());
					Log.e("result", "aque.size():" + aque.size());
					if (que.size() == 0 || aque.size() == 0) // que=lista
																// question or
																// aque=lista
																// stringa
						in.appendText(result);
					else if (que.size() != 0)
						aque.add(result);
					else
						in.appendText(aque.poll());

				} catch (Exception E) {
					Log.e("JESS.Smart Travel Assistant",
							"input routing error:", E);
				}
			} else {
				// in.clear();
				// RISULTATO UGUALE A START
				s = "";
			}

			Log.d("JESS.Smart Travel Assistant", "getting question");
			if (que.size() != 0) {
				// TODO::
				System.out.println("loop");
			} else {
				do {
					s = "";
					try {
						while (s == "") {
							engine.executeCommand("(facts)");
							engine.executeCommand("(agenda)");
							engine.run(1); // analizza tutte le regole finché
											// non prende la stringa di una dove
											// c'è il printout
							s = out.getBuffer() + "";
							Log.v("resultAfterRun", "ecco s: " + s);
							engine.executeCommand("(facts)");
						}

					} catch (JessException E) {
					}

					pw.flush();
					out.flush();
					out.getBuffer().setLength(0);
					if (s.equals(LastOutput))
						Log.v("JESS.Smart Travel Assistant",
								"duplicate encounter");

				} while (s.equals(LastOutput));
				LastOutput = s;

				System.out.println(s);
				// String s= out.getBuffer()+"";
				// Question temp=null;
				if (s.length() > 1) {
					while (s.split("::").length >= 2) {
						try {
							engine.executeCommand("(agenda)");
						} catch (JessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Question temp = null;

						Log.v("questionp", "processing question" + s);
						if (s.startsWith("YN::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.YN, 0);
							// memory=temp;
						} else if (s.startsWith("SA::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.SA, 0);
							// memory=temp;

						} else if (s.startsWith("ss::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.statement, 0);
							// memory=temp;

						} else if (s.startsWith("13::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.number, 3);
							temp.max = 3;
							// memory=temp;
						} else if (s.startsWith("14::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.number, 4);
							temp.max = 4;
							// memory=temp;

						} else if (s.startsWith("rr::")) {

							temp = new Question(s.split("::")[1].toString(),
									Question.Type.terminate, 0);
							// memory=temp;
						} else {
							s = s.substring(s.indexOf("::"), s.length());
							temp = new Question(s, Question.Type.terminate, 0);
							logicComplete = true;
						}
						s = s.substring(s.indexOf("::") + 2, s.length());
						if ((s.indexOf("::") > 2))
							s = s.substring(s.indexOf("::") - 2, s.length());
						que.add(temp);
						Log.v("question", "adding Q:" + temp.question);
						Log.v("question", temp.questionType.toString());
					}
				} else {
					Log.wtf("JESS.Smart Travel Assistant", "blank string s:"
							+ s);
				}

			}
		}
		memory = que.poll();
		return memory;

	}

	@Deprecated
	// unsafe.
	public Question next() {
		{
			throw new NoSuchElementException();
		}
		// return null;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}