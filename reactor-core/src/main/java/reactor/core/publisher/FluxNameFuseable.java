/*
 * Copyright (c) 2011-2017 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactor.core.publisher;

import java.util.Set;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

/**
 * An operator that just bears a name or a set of tags, which can be retrieved via the
 * {@link Attr#TAGS TAGS}
 * attribute.
 *
 * @author Simon Baslé
 * @author Stephane Maldini
 */
final class FluxNameFuseable<T> extends FluxOperator<T, T> implements Fuseable {

	final String name;

	final Set<Tuple2<String, String>> tags;

	FluxNameFuseable(Flux<? extends T> source,
			@Nullable String name,
			@Nullable Set<Tuple2<String, String>> tags) {
		super(source);
		this.name = name;
		this.tags = tags;
	}

	@Override
	public void subscribe(CoreSubscriber<? super T> actual) {
		source.subscribe(actual);
	}

	@Nullable
	@Override
	public Object scanUnsafe(Attr key) {
		if (key == Attr.NAME) {
			return name;
		}

		if (key == Attr.TAGS && tags != null) {
			return tags.stream();
		}

		return super.scanUnsafe(key);
	}


}
