/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.conversation;

import java.util.List;

import org.worldgrower.World;
import org.worldgrower.WorldObject;

/**
 * An InterceptedConversation is used when the target replies something that isn't based on the question,
 * but on the state of the performer.
 *
 */
public interface InterceptedConversation {

	public Response getReplyPhrase(ConversationContext conversationContext);
	public List<Response> getReplyPhrases(ConversationContext conversationContext);

	public boolean isConversationAvailable(WorldObject performer, WorldObject target, World world);

	public void handleResponse(int replyIndex, ConversationContext conversationContext, Conversation originalConversation);

	public default Response getReply(List<Response> replyPhrases, int replyId) {
		for(Response response : replyPhrases) {
			if (response.getId() == replyId) {
				return response;
			}
		}
		throw new IllegalStateException("replyId " + replyId + " not found in responses: " + replyPhrases);
	}
}
