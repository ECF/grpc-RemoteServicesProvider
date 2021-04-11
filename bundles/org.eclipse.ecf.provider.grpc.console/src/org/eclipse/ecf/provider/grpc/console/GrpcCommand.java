/*******************************************************************************
 * Copyright (c) 2021 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.ecf.provider.grpc.console;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
		"osgi.command.function=listservers", 
		"osgi.command.function=lsvr",
		"osgi.command.function=togglereflection",
		"osgi.command.function=trf",
		"osgi.command.function=addreflection",
		"osgi.command.function=arf",
		"osgi.command.function=removereflection",
		"osgi.command.function=rrf" }, service = { GrpcCommand.class, Converter.class })
public class GrpcCommand extends AbstractCommand implements Converter {

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

	@Descriptor("List ECF GRPC Host Containers")
	public List<GRPCHostContainer> listservers(CommandSession cs) {
		consoleLine(cs, GRPCHOSTCONTAINER_LINE_FORMAT, "ID", "ProtoReflectionService");
		return getHostContainers();
	}

	@Descriptor("List ECF GRPC Host Containers")
	public List<GRPCHostContainer> lsvr(CommandSession cs) {
		consoleLine(cs, GRPCHOSTCONTAINER_LINE_FORMAT, "ID", "ProtoReflectionService");
		return getHostContainers();
	}

	public GRPCHostContainer listservers(@Descriptor("GRPC port of container to list") GRPCHostContainer container) {
		return container;
	}

	public GRPCHostContainer lsvr(@Descriptor("GRPC port of container to list") GRPCHostContainer container) {
		return container;
	}

	@Descriptor("Toggle (active/inactive) GRPC ProtoReflectionService")
	public List<GRPCHostContainer> togglereflection(CommandSession cs) {
		List<GRPCHostContainer> ghcs = getHostContainers();
		ghcs.forEach(c -> {
			c.toggleProtoReflectionServiceDef();
		});
		return ghcs;
	}

	@Descriptor("Toggle (active/inactive) GRPC ProtoReflectionService")
	public List<GRPCHostContainer> trf(CommandSession cs) {
		return togglereflection(cs);
	}

	public GRPCHostContainer togglereflection(
			@Descriptor("GRPC port of container to toggle reflection") GRPCHostContainer container) {
		return container;
	}

	public GRPCHostContainer trf(
			@Descriptor("GRPC port of container to toggle reflection") GRPCHostContainer container) {
		return container;
	}

	@Descriptor("Add/activate GRPC ProtoReflectionService")
	public List<GRPCHostContainer> addreflection(CommandSession cs) {
		List<GRPCHostContainer> ghcs = getHostContainers();
		ghcs.forEach(c -> {
			c.addProtoReflectionServiceDef();
		});
		return ghcs;
	}

	@Descriptor("Add/activate GRPC ProtoReflectionService")
	public List<GRPCHostContainer> arf(CommandSession cs) {
		return addreflection(cs);
	}

	public GRPCHostContainer addreflection(
			@Descriptor("GRPC port of container to activate reflection") GRPCHostContainer container) {
		return container;
	}

	public GRPCHostContainer arf(
			@Descriptor("GRPC port of container to activate reflection") GRPCHostContainer container) {
		return container;
	}

	@Descriptor("Remove/deactivate GRPC ProtoReflectionService")
	public List<GRPCHostContainer> removereflection(CommandSession cs) {
		List<GRPCHostContainer> ghcs = getHostContainers();
		ghcs.forEach(c -> {
			c.removeProtoReflectionServiceDef();
		});
		return ghcs;
	}

	@Descriptor("Add/activate GRPC ProtoReflectionService")
	public List<GRPCHostContainer> rrf(CommandSession cs) {
		return removereflection(cs);
	}

	public GRPCHostContainer removereflection(
			@Descriptor("GRPC port of container to remove reflection") GRPCHostContainer container) {
		return container;
	}

	public GRPCHostContainer rrf(
			@Descriptor("GRPC port of container to remove reflection") GRPCHostContainer container) {
		return container;
	}

	protected static final String GRPCHOSTCONTAINER_LINE_FORMAT = "%1$-45s| %2$-35s\n"; //$NON-NLS-1$

	protected String formatGRPCContainer(GRPCHostContainer c, int level, Converter escape) {
		return formatLine(GRPCHOSTCONTAINER_LINE_FORMAT, c.getID().getName(),
				c.isProtoReflectionServiceDef() ? "active" : "inactive");
	}

	public Object convert(Class<?> desiredType, Object in) throws Exception {
		if (desiredType == GRPCHostContainer.class && in instanceof String)
			return getGRPCContainerForId((String) in);
		return null;
	}

	private GRPCHostContainer getGRPCContainerForId(String in) {
		Optional<GRPCHostContainer> optional = getHostContainers().stream().filter(g -> {
			ID gid = g.getID();
			int port = ((URIID) gid).toURI().getPort();
			return gid.getName().equals(in) || in.equals(String.valueOf(port));
		}).findFirst();
		return optional.get();
	}

	public String format(Object target, int level, Converter escape) {
		if (target instanceof GRPCHostContainer)
			return formatGRPCContainer((GRPCHostContainer) target, level, escape);
		return null;
	}

}
