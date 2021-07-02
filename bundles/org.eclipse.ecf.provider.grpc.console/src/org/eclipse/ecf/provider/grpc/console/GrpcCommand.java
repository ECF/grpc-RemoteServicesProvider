/*******************************************************************************
 * Copyright (c) 2021 Composent Inc., and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.console;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Converter;
import org.apache.felix.service.command.Descriptor;
import org.eclipse.ecf.console.AbstractCommand;
import org.eclipse.ecf.core.IContainerManager;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IIDFactory;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.core.identity.URIID;
import org.eclipse.ecf.provider.grpc.host.GRPCHostContainer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(property = { "osgi.command.scope=ecf-grpc", 
		"osgi.command.function=listreflection", 
		"osgi.command.function=lr",
		"osgi.command.function=addreflection",
		"osgi.command.function=ar",
		"osgi.command.function=removereflection",
		"osgi.command.function=rr"}, service = { GrpcCommand.class })
public class GrpcCommand extends AbstractCommand {

	private IContainerManager containerManager;
	private IIDFactory idFactory;

	@Reference
	void bindContainerManager(IContainerManager cm) {
		this.containerManager = cm;
	}

	void unbindContainerManager(IContainerManager cm) {
		this.containerManager = null;
	}

	@Reference
	void bindIdentityFactory(IIDFactory idf) {
		this.idFactory = idf;
	}

	void unbindIdentityFactory(IIDFactory idf) {
		this.idFactory = null;
	}

	@Override
	protected IContainerManager getContainerManager() {
		return this.containerManager;
	}

	@Override
	protected IIDFactory getIDFactory() {
		return this.idFactory;
	}

	protected List<GRPCHostContainer> getHostContainers() {
		return Arrays.asList(getContainerManager().getAllContainers()).stream().filter(c -> {
			ID cid = c.getID();
			if (cid != null) {
				Namespace ns = cid.getNamespace();
				if ("grpc".equalsIgnoreCase(ns.getScheme())) {
					return ((((URIID) cid).toURI()).getPort() > 0);
				}
			}
			return false;
		}).filter(c -> {
			return c instanceof GRPCHostContainer;
		}).map(c -> {
			return (GRPCHostContainer) c;
		}).collect(Collectors.toList());
	}

	private List<String> formatGRPCContainers(List<GRPCHostContainer> containers, CommandSession cs) {
		consoleLine(cs, GRPCHOSTCONTAINER_LINE_FORMAT, "Server", "Reflection");
		return containers.stream().map(g -> {
			return formatGRPCContainer(g, 0, null);
		}).collect(Collectors.toList());
	}
	
	@Descriptor("List Reflection state of gRPC servers")
	public List<String> listreflection(CommandSession cs) {
		return formatGRPCContainers(getHostContainers(),cs);
	}

	@Descriptor("List Reflection state of gRPC servers")
	public List<String> lr(CommandSession cs) {
		return formatGRPCContainers(getHostContainers(), cs);
	}

	@Descriptor("Add/activate Reflection of gRPC servers")
	public List<String> addreflection(CommandSession cs) {
		List<GRPCHostContainer> ghcs = getHostContainers();
		ghcs.forEach(c -> {
			c.addProtoReflectionServiceDef();
		});
		return formatGRPCContainers(ghcs, cs);
	}

	@Descriptor("Add/activate Reflection of gRPC servers")
	public List<String> ar(CommandSession cs) {
		return addreflection(cs);
	}

	@Descriptor("Remove/deactivate Reflection of gRPC servers")
	public List<String> removereflection(CommandSession cs) {
		List<GRPCHostContainer> ghcs = getHostContainers();
		ghcs.forEach(c -> {
			c.removeProtoReflectionServiceDef();
		});
		return formatGRPCContainers(ghcs, cs);
	}

	@Descriptor("Remove/deactivate Reflection of gRPC servers")
	public List<String> rr(CommandSession cs) {
		return removereflection(cs);
	}

	protected static final String GRPCHOSTCONTAINER_LINE_FORMAT = "%1$-45s|%2$-35s\n"; //$NON-NLS-1$

	protected String formatGRPCContainer(GRPCHostContainer c, int level, Converter escape) {
		return formatLine(GRPCHOSTCONTAINER_LINE_FORMAT, c.getID().getName(),
				c.isProtoReflectionServiceDef() ? "active" : "inactive");
	}

}
