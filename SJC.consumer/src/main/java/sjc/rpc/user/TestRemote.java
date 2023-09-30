package sjc.rpc.user;

import sjc.rpc.cousumer.param.Response;

public interface TestRemote {
	public Response testUser(User user);

	public void saveUser(User user);
}
