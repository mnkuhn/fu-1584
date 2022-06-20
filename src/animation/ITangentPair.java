package animation;

import propra22.q8493367.draw.model.IHull;
import propra22.q8493367.point.IPoint;

public interface ITangentPair {

	void step();

	IPoint[] getTangent1();

	IPoint[] getTangent2();

	void initialize(IHull hull, float diagonal);

	void stop();

	void setLength(float lenght);

}
