/*
 * Copyright (c) 2022 The AnJia Authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model

type EurekaAppsResp struct {
	Applications EurekaApps `json:"applications"`
}
type EurekaApps struct {
	Application []EurekaApp `json:"application"`
}
type EurekaAppResp struct {
	Application EurekaApp `json:"application"`
}
type EurekaApp struct {
	Name     string           `json:"name"`
	Instance []EurekaInstance `json:"instance"`
}
type EurekaInstance struct {
	HomePageUrl string            `json:"homePageUrl"`
	Status      string            `json:"status"`
	IpAddr      string            `json:"ipAddr"`
	Metadata    map[string]string `json:"metadata"`
	InstanceId  string            `json:"instanceId"`
}
